import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { config } from './app/app.config.server';
import { EshopComponent } from './eshop/eshop.component';

const bootstrap = () => bootstrapApplication(EshopComponent, config);

export default bootstrap;
