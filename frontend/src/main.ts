import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { EshopComponent } from './eshop/eshop.component';

bootstrapApplication(EshopComponent, appConfig)
  .catch((err) => console.error(err));
