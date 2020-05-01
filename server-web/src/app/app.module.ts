import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {APP_BASE_HREF} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AdComponent } from './ad/ad.component';

import { AdsComponent } from './ads/ads.component';
import { AdsService } from './services/ads.service';
import { AuthComponent } from './auth/auth.component';
import {AuthService} from './services/auth.service';
import { RemarksComponent } from './remarks/remarks.component';
import {RemarksService} from './services/remarks.service';

const appRoutes: Routes = [
  { path: 'auth', component: AuthComponent },
  { path: 'ad', component: AdsComponent },
  { path: 'home', component: HomeComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdsComponent,
    AdComponent,
    AuthComponent,
    RemarksComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: ''},
    AdsService,
    AuthService,
    RemarksService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
