import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Subject} from 'rxjs';
import Swal from 'sweetalert2';
import {Router} from '@angular/router';

@Injectable()
export class AuthService {
  private auth = -1;
  authSubject = new Subject<any>();
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic ' + btoa('username:password')
    })
  };

  constructor(private httpClient: HttpClient, private router: Router) { }

  emit() {
    this.authSubject.next(this.auth);
  }

  connection(email: string, password: string) {
    this.httpClient.get<any>('/companies/auth?email=' + email + '&password=' + password, this.httpOptions)
      .subscribe(
        (res) => {
          this.auth = res;
          this.emit();
          if (this.auth > 0) {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Vous êtes connecté'
            }).then(() => {
              this.router.navigate(['ad']);
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Vous n\'êtes pas connecté'
            });
          }
        },
        (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Erreur',
            text: 'Vous n\'êtes pas connecté'
          });
        });
  }

  registration(corporateName: string, siren: number, address: string, email: string, password: string) {
    this.httpClient.post('/companies', {
      'corporateName': corporateName,
      'siren': siren,
      'address': address,
      'email': email,
      'password': password
    }, this.httpOptions)
      .subscribe(
        (res) => {
          Swal.fire({
            icon: 'success',
            title: 'Succès',
            text: 'Votre compte a été créé avec succès'
          });
          this.connection(email, password);
        },
        (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Erreur',
            text: 'Votre compte n\'a pas été créé'
          });
        });
  }
}
