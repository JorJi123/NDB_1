import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { Client } from './client.model';
import { Product } from './product.model';
import { EshopService } from './eshop.service';
import { ListboxModule } from 'primeng/listbox';
import { Observable, of } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { Order } from './order.model';
import { InputNumberModule } from 'primeng/inputnumber';
import { NgFor } from '@angular/common';



@Component({
  selector: 'app-eshop',
  standalone: true,
  imports: [ButtonModule,
            TableModule,
            InputTextModule, 
            FormsModule,
            ListboxModule,
            AsyncPipe,
            InputNumberModule,
            NgFor
  ],
  templateUrl: './eshop.component.html',
  styleUrl: './eshop.component.css'
})
export class EshopComponent {

  availableProducts$:Observable<Product[]>;
  selectedProducts:Product[] = [];

  dataObservable:Observable<any> = of(null);
  dataColumns:any[] = [];
  dataBody:any[] =[];
  orderObservable:Observable<any> = of(null);

  constructor(private eshopService:EshopService){
    this.availableProducts$ = eshopService.getAllProducts();
  }

  
  formData:Client = {
    id: "",
    name: "",
    email: ""
  };
  formData2:Product = {
    id: '',
    name: '',
    category:'',
    price: 0.0
  };
  formData3:Order = {
    clientId:'',
    items:[]
  }

  SubmitClient(){
    this.eshopService.registerClient(this.formData).subscribe(console.log);
  }
  SubmitProduct(){
    this.eshopService.registerProduct(this.formData2).subscribe();
    this.availableProducts$ = this.eshopService.getAllProducts();
    this.availableProducts$.subscribe();
  }
  SubmitOrder(){
    this.eshopService.registerOrder(this.formData3).subscribe();
  }
  updateDataItems(){
    const productList = this.selectedProducts.map((product) =>{
      const existingItem = this.formData3.items.find(item => item.productId === product.id)
      if(existingItem) return {productId:product.id, quantity: existingItem.quantity }
      else return {productId:product.id, quantity: 1};
    })
    this.formData3.items = productList;
    console.log(this.formData3);
  }

  getProductQuantity(id:string){
  }

  setProductQuantity(id:string, event:any){
    const existingItem = this.formData3.items.find(item => item.productId === id);
    
    if (existingItem) {
      existingItem.quantity = event;
    }
    console.log(this.formData3);
  }

  getClientDetails(event:any){
    this.dataObservable = this.eshopService.getClientDetails(event);
    this.dataObservable.subscribe({
      next: (value:any) => {
        console.log(value) ;
        this.resetBody();
        this.resetColumns();
        this.setColumns(value);
        this.setBody(value);
      },
      error: (err:any) => {
        this.resetBody();
      }
    })
  }

  getProductDetails(event:any){
    this.dataObservable = this.eshopService.getProductDetails(event);
    this.dataObservable.subscribe({
      next: (value:any) => {
        console.log(value) ;
        this.resetBody();
        this.resetColumns();
        this.setBody(value);
        this.setColumns(value);
      },
      error: (err:any) => {
        this.resetBody();
      }
    })
  }
  deleteClient(event:any){
    this.eshopService.deleteClient(event).subscribe();
    this.getClientDetails(event);
  }

  deleteProduct(event:any){
    this.eshopService.deleteProduct(event).subscribe();
    this.getProductDetails(event);
  }

  getClientOrders(event:any){
   return this.orderObservable = this.eshopService.getClientOrders(event);
  }

  getTopClients(){
    this.dataObservable = this.eshopService.getTopClients();
    this.dataObservable.subscribe({
      next: (value:any) => {
        this.resetBody();
        this.resetColumns();
        this.setBody(value);
        this.setColumns(value);
      },
      error: (err:any) => {
        this.resetBody();
      }
    })
  }

  getTopProducts(){
    this.dataObservable = this.eshopService.getTopProducts();
    this.dataObservable.subscribe({
      next: (value:any) => {
        this.resetBody();
        this.resetColumns();
        this.setBody(value);
        this.setColumns(value);
      },
      error: (err:any) => {
        this.resetBody();
      }
    })
  }

  setColumns(object:any){
    if(object instanceof Array)this.dataColumns = Object.keys(object[0])
    else{
      this.dataColumns = Object.keys(object);
      console.log(this.dataColumns)
    }

  }

  setBody(object:any){
    this.dataBody = this.dataBody.concat(object);
    console.log(this.dataBody);
  }

  resetColumns(){
    this.dataColumns = [];
  }

  resetBody(){
    this.dataBody = [];
  }

  listBoxClick(event:any){
    this.resetBody();
    this.resetColumns();
    this.setColumns(event.value.items)
    this.setBody(event.value.items)
  }

  resetButton(){
    this.resetBody();
    this.resetColumns();
  }

  getAll(id:string){
    this.resetBody();
    this.resetColumns();
    this.dataObservable = this.getClientOrders(id);
    this.dataObservable.subscribe({
      next: (value:any) => {
        for(let element in value){
          this.setColumns(value[element].items);
          this.setBody(value[element].items);
        }
      },
      error: (err:any) => {
        this.resetBody();
      }
    })
  }

  dropDatabase(){
    this.eshopService.deleteAll().subscribe();
    this.dataObservable = of(null);
    this.resetBody();
    this.resetColumns();
    this.availableProducts$ = this.eshopService.getAllProducts();
    this.availableProducts$.subscribe();
  }

}
