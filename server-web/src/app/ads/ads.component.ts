import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdsService} from '../services/ads.service';
import {Subscription} from 'rxjs';
import Swal from 'sweetalert2';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-ads',
  templateUrl: './ads.component.html',
  styleUrls: ['./ads.component.css']
})
export class AdsComponent implements OnInit, OnDestroy {

  auth;
  authSubcription: Subscription;
  ads: any[];
  adsSubcription: Subscription;

  constructor(private authService: AuthService, private adsService: AdsService) {}

  ngOnInit() {
    this.authSubcription = this.authService.authSubject.subscribe(
      (auth: any) => {
        this.auth = auth;
      }
    );
    this.authService.emit();
    this.adsSubcription = this.adsService.adsSubject.subscribe(
      (ads: any[]) => {
        this.ads = ads;
      }
    );
    this.adsService.emit();
    if (this.auth < 0) {
      this.adsService.getAllAds();
    } else {
      this.adsService.getAds(this.auth);
    }
  }

  ngOnDestroy() {
    this.adsSubcription.unsubscribe();
  }

  postAds() {
    Swal.mixin({
      input: 'text',
      confirmButtonText: 'Next',
      showCancelButton: true,
      progressSteps: ['1', '2', '3']
    }).queue([
      {
        title: 'Étape 1',
        text: 'Choisir un titre pour votre annonce'
      },
      {
        title: 'Étape 2',
        text: 'Choisir une description pour votre annonce'
      },
      {
        title: 'Étape 3',
        text: 'Choisir une rémunération pour votre annonce'
      }
    ]).then((result) => {
      if (result.value) {
        this.adsService.postAds(this.auth, result.value[0], result.value[1], result.value[2]).subscribe(
          (res) => {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Votre annonce a été créé avec succès'
            });
            this.adsService.getAds(this.auth);
          },
          (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Votre annonce n\'a pas été créé'
            });
          });
      }
    });
  }
}
