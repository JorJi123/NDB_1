import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { Client } from './client.model';
import { Product } from './product.model';
import { ListboxModule } from 'primeng/listbox';
import { EshopService } from './eshop.service'; 
@Component({
  selector: 'app-eshop',
  standalone: true,
  imports: [ListboxModule, ButtonModule, TableModule, InputTextModule, FormsModule],
  templateUrl: './eshop.component.html',
  styleUrl: './eshop.component.css'
})
export class EshopComponent {
  cities: {label: string, value: string}[] = [
    {label: 'Client', value: '/url'},
    {label: 'Order', value: 'City2'},
    {label: 'Product', value: 'City3'}
  ];
   selectedCity1: string="";
   selectedCity2: string="";
   

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

  SubmitClient(){
    this.eshopService.registerClient(this.formData).subscribe();
    console.log(this.formData);
  };
}
