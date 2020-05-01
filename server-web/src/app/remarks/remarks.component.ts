import {Component, Input, OnInit} from '@angular/core';
import {RemarksService} from '../services/remarks.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-remarks',
  templateUrl: './remarks.component.html',
  styleUrls: ['./remarks.component.css']
})
export class RemarksComponent implements OnInit {
  @Input() remark: any;

  constructor(private remarksService: RemarksService) { }

  ngOnInit() {}

  putRemarks() {
    Swal.mixin({
      input: 'text',
      confirmButtonText: 'Next',
      showCancelButton: true,
      progressSteps: ['1', '2']
    }).queue([
      {
        title: 'Étape 1',
        text: 'Choisir un nouveau titre pour votre commentaire',
        inputValue: this.remark.title
      },
      {
        title: 'Étape 2',
        text: 'Choisir un nouveau contenu pour votre commentaire',
        inputValue: this.remark.remark
      }
    ]).then((result) => {
      if (result.value) {
        this.remarksService.putRemarks(
          this.remark.idAd,
          this.remark.idRemark,
          result.value[0],
          result.value[1]).subscribe(
          (res) => {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Votre commentaire a été modifié avec succès'
            });
            this.remarksService.getRemarks(this.remark.idAd);
          },
          (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Votre commentaire n\'a pas été modifié'
            });
          });
      }
    });
  }

  deleteRemarks() {
    Swal.fire({
      icon: 'warning',
      title: 'Êtes-vous sûr de vouloir supprimer ce commentaire ?',
      text: 'cette action est irréversible',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete'
    }).then((result) => {
      if (result.value) {
        this.remarksService.deleteRemarks(this.remark.idAd, this.remark.idRemark).subscribe(
          (res) => {
            Swal.fire({
              icon: 'success',
              title: 'Succès',
              text: 'Votre commentaire a été supprimé avec succès'
            });
            this.remarksService.getRemarks(this.remark.idAd);
          },
          (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Erreur',
              text: 'Votre commentaire n\'a pas été supprimé'
            });
          });
      }
    });
  }
}
