import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Warehouse } from "./warehouse.model";

@Injectable({
    providedIn:'root'
})
export class appService{
    constructor(private http:HttpClient){}

    getWarehouse(){
        return this.http.get<Object>('http://localhost:8080/test');
    }

    postWarehouse(form:Warehouse){
        const body = new HttpParams;
            body.set('id', form.id)
            body.set('adress', form.address)
            body.set('plotas', form.area)
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<Warehouse>('http://localhost:8080/warehouse', form);
    }

    getWarehouseById(id:string){
        const header = new HttpHeaders(id);
        const url = 'http://localhost:8080/warehouse/' + id;
        console.log(url)
        return this.http.get<Warehouse[]>(url)
    }
}