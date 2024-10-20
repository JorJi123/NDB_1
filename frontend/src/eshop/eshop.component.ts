import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { AsyncPipe, NgIf } from '@angular/common';
import { NgModule } from '@angular/core';
import { app } from '../../server';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { Client } from './client.model';
import { Product } from './product.model';

@Component({
  selector: 'app-eshop',
  standalone: true,
  imports: [ButtonModule, TableModule, InputTextModule, FormsModule],
  templateUrl: './eshop.component.html',
  styleUrl: './eshop.component.css'
})
export class EshopComponent {
  Submit(){
    console.log("test")
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
