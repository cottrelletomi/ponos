import { Component, OnInit, Input } from '@angular/core';
import Swal from 'sweetalert2';
import {AdsService} from '../services/ads.service';
import {Subscription} from 'rxjs';
import {RemarksService} from '../services/remarks.service';

@Component({
  selector: 'app-ad',
  templateUrl: './ad.component.html',
  styleUrls: ['./ad.component.css']
})
export class AdComponent implements OnInit {
  @Input() auth: any;
  @Input() ad: any;
  remarks: any[];
  remarksSubcription: Subscription;

  constructor(private adsService: AdsService, private  remarksService: RemarksService) { }

  ngOnInit() {
    this.remarksSubcription = this.remarksService.remarksSubject.subscribe(
      (remarks: any[]) => {
        this.remarks = remarks;
      }
    );
    this.remarksService.emit();
    this.remarksService.getRemarks(this.ad.idAd);
  }

  putAds() {
    Swal.mixin({
      input: 'text',
      confirmButtonText: 'Next',
      showCancelButton: true,
      progressSteps: ['1', '2', '3']
    }).queue([
      {
        title: 'Étape 1',
        text: 'Choisir un nouveau titre pour votre annonce',
        inputValue: this.ad.title
      },
      {
        title: 'Étape 2',
        text: 'Choisir une nouvelle description pour votre annonce',
        inputValue: this.ad.description
      },
      {
        title: 'Étape 3',
        text: 'Choisir une nouvelle rémunération pour votre annonce',
        inputValue: this.ad.remuneration
      }
    ]).then((result) => {
      if (result.value) {
        this.adsService.putAds(
          this.ad.idCompany,
          this.ad.idAd,
          result.value[0],
          result.value[1],
          result.value[2]).subscribe(
          (res) => {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Votre annonce a été modifié avec succès'
            });
            this.adsService.getAds(this.ad.idCompany);
          },
          (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Votre annonce n\'a pas été modifié'
            });
          });
      }
    });
  }

  deleteAds() {
    Swal.fire({
      icon: 'warning',
      title: 'Êtes-vous sûr de vouloir supprimer cette annonce ?',
      text: 'cette action est irréversible',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete'
    }).then((result) => {
      if (result.value) {
        this.adsService.deleteAds(this.ad.idCompany, this.ad.idAd).subscribe(
          (res) => {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Votre annonce a été supprimé avec succès'
            });
            this.adsService.getAds(this.ad.idCompany);
          },
          (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Votre annonce n\'a pas été supprimé'
            });
          });
      }
    });
  }

  postRemarks() {
    Swal.mixin({
      input: 'text',
      confirmButtonText: 'Next',
      showCancelButton: true,
      progressSteps: ['1', '2']
    }).queue([
      {
        title: 'Étape 1',
        text: 'Choisir un titre pour votre commentaire'
      },
      {
        title: 'Étape 2',
        text: 'Choisir un contenu pour votre commentaire'
      }
    ]).then((result) => {
      if (result.value) {
        this.remarksService.postRemarks(this.ad.idAd, result.value[0], result.value[1]).subscribe(
          (res) => {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Votre commentaire a été créé avec succès'
            });
            this.remarksService.getRemarks(this.ad.idAd);
          },
          (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Votre commentaire n\'a pas été créé'
            });
          });
      }
    });
  }
}
