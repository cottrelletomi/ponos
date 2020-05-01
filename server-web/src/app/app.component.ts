import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  menuHandler() {
    const body = document.querySelector('body');
    body.classList.toggle('is-menu-visible');
  }
}
