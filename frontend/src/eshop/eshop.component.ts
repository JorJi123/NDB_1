import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { Client } from './client.model';
import { Product } from './product.model';
import { EshopService } from './eshop.service';

@Component({
  selector: 'app-eshop',
  standalone: true,
  imports: [ButtonModule,
            TableModule,
            InputTextModule, 
            FormsModule,
  ],
  templateUrl: './eshop.component.html',
  styleUrl: './eshop.component.css'
})
export class EshopComponent {

  constructor(private eshopService:EshopService){}

  Submit(){
    this.eshopService.getAllProducts().subscribe(console.log);
  };
  formData:Client = {
    id: '',
    name: '',
    email: ''
  };
  formData2:Product = {
    id: '',
    name: '',
    description: '',
    price: 0.0
  };
}
