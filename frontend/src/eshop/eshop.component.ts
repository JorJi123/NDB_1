import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { Client } from './client.model';
import { Product } from './product.model';
import { EshopService } from './eshop.service';
import { ListboxModule } from 'primeng/listbox';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { Order } from './order.model';
import { InputNumberModule } from 'primeng/inputnumber';



@Component({
  selector: 'app-eshop',
  standalone: true,
  imports: [ButtonModule,
            TableModule,
            InputTextModule, 
            FormsModule,
            ListboxModule,
            AsyncPipe,
            InputNumberModule
  ],
  templateUrl: './eshop.component.html',
  styleUrl: './eshop.component.css'
})
export class EshopComponent {
  availableProducts$:Observable<Product[]>;
  selectedProducts:Product[] = [];

  constructor(private eshopService:EshopService){
    this.availableProducts$ = eshopService.getAllProducts();
  }

  

  Submit(){
    //this.eshopService.getAllProducts().subscribe(console.log);
    console.log(this.formData3);
  };
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


}
