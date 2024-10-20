import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { from, Observable } from "rxjs";
import { Product } from "./product.model";
import { Client } from "./client.model";
import { Order } from "./order.model";

@Injectable({
    providedIn:'root'
})
export class EshopService{
    constructor(private http:HttpClient){}

    getAllProducts():Observable<Product[]>{
        return this.http.get<Product[]>('products');
    }

    registerClient(form:Client){
        const headers = new HttpHeaders('Content-Type: application/json')
        return this.http.put('clients',form, {headers: headers});
    }

    registerOrder(form:Order){
        return this.http.put('orders', form);
    }

    registerProduct(form:Product){
        return this.http.put('products', form);
    }
}