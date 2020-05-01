import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Subscription} from 'rxjs';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit, OnDestroy {

  private auth: number;
  authSubcription: Subscription;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authSubcription = this.authService.authSubject.subscribe(
      (auth: number) => {
        this.auth = auth;
      }
    );
    this.authService.emit();
  }

  ngOnDestroy() {
    this.authSubcription.unsubscribe();
  }

  connection(form: NgForm) {
    this.authService.connection(form.value['email'], form.value['password']);
  }

  registration(form: NgForm) {
    this.authService.registration(
      form.value['corporate_name'],
      form.value['siren'],
      form.value['address'],
      form.value['email'],
      form.value['password']);
  }
}
