import { HttpClient } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";

@Injectable({
    providedIn:'root'
})
export class EshopService{
    constructor(private http:HttpClient){}

    getAllProducts(){
        return this.http.get('products');
    }
}