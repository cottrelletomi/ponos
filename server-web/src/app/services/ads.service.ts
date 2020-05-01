import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class AdsService {
  adsSubject = new Subject<any[]>();
  private ads = [];
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic ' + btoa('username:password')
    })
  };

  constructor(private httpClient: HttpClient) { }

  emit() {
    this.adsSubject.next(this.ads.slice());
  }

  postAds(idCompany: number, title: string, description: string, remuneration: number) {
    return this.httpClient.post('/companies/' + idCompany + '/ads',
      {
        'title': title,
        'description': description,
        'remuneration': remuneration
    }, this.httpOptions);
  }

  getAds(idCompany: number) {
    this.httpClient.get<any[]>('/companies/' + idCompany + '/ads', this.httpOptions)
      .subscribe(
        (res) => {
          this.ads = res;
          this.emit();
        },
        (err) => {
          console.log(err);
        });
  }

  getAllAds() {
    this.httpClient.get<any[]>('/companies/ads', this.httpOptions)
      .subscribe(
        (res) => {
          this.ads = res;
          this.emit();
        },
        (err) => {
          console.log(err);
        });
  }

  putAds(idCompany: number, idAd: number, title: string, description: string, remuneration: number) {
    return this.httpClient.put('/companies/' + idCompany + '/ads/' + idAd,
      {
        'title': title,
        'description': description,
        'remuneration': remuneration
      }, this.httpOptions);
  }

  deleteAds(idCompany: number, idAd: number) {
    return this.httpClient.delete('/companies/' + idCompany + '/ads/' + idAd, this.httpOptions);
  }

}
