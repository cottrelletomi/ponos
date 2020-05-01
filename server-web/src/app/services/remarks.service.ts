import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class RemarksService {
  remarksSubject = new Subject<any[]>();
  private remarks = [];
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic ' + btoa('username:password')
    })
  };

  constructor(private httpClient: HttpClient) { }

  emit() {
    this.remarksSubject.next(this.remarks.slice());
  }

  postRemarks(idAd: number, title: string, remark: string) {
    return this.httpClient.post('/ads/' + idAd + '/remarks',
      {
        'title': title,
        'remark': remark
      }, this.httpOptions);
  }

  getRemarks(idAd: number) {
    this.httpClient.get<any[]>('/ads/' + idAd + '/remarks', this.httpOptions)
      .subscribe(
        (res) => {
          this.remarks = res;
          this.emit();
        },
        (err) => {
          console.log(err);
        });
  }

  putRemarks(idAd: number, idRemark: number, title: string, remark: string) {
    return this.httpClient.put('/ads/' + idAd + '/remarks/' + idRemark,
      {
        'title': title,
        'remark': remark
      }, this.httpOptions);
  }

  deleteRemarks(idAd: number, idRemark: number) {
    return this.httpClient.delete('/ads/' + idAd + '/remarks/' + idRemark, this.httpOptions);
  }

}
