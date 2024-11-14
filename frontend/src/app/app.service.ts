import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Warehouse } from "./warehouse.model";
import { Inventory } from "./inventory.model";
import { from } from "rxjs";

@Injectable({
    providedIn:'root'
})
export class appService{
    constructor(private http:HttpClient){}

    postWarehouse(form:Warehouse){
        const body = new HttpParams;
            body.set('id', form.id)
            body.set('adress', form.adresas)
            body.set('plotas', form.plotasM2)
        return this.http.put<Warehouse>('http://localhost:8080/warehouse', form);
    }

    getWarehouseById(id:string){
        const url = 'http://localhost:8080/warehouse/' + id;
        return this.http.get<Warehouse[]>(url)
    }

    deleteWarehouse(id:string){
        const url = 'http://localhost:8080/warehouse/' + id;
        return this.http.delete(url)
    }

    getInventories(id:string){
        const url = 'http://localhost:8080/warehouse/' + id + '/inventory';
        return this.http.get(url);
    }

    putInventory(id:string, form:any){
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
          })
        const body = new HttpParams
            body.set('id', form.id)
            body.set('amount', form.amount)
            //console.log(body, f);
        const url = `http://localhost:8080/warehouse/${id}/inventory`;
        return this.http.put(url, {id:form.id, amount:form.amount}, {headers: headers});
    }
    getInventoryAmount(whId:string, invId:string){
        const url = `http://localhost:8080/warehouse/${whId}/inventory/${invId}`;
        return this.http.get(url);
    }
    postInventoryAmount(whId:string, invId:string, newAmount:number){
        const body = new HttpParams;
            body.set('amount', newAmount)
        const url = `http://localhost:8080/warehouse/${whId}/inventory/${invId}`;
        return this.http.post(url, body);
    }
    deleteInventory(whId:string ,invId:string){
        const url = `http://localhost:8080/warehouse/${whId}/inventory/${invId}`;
        return this.http.delete(url);
    }
    changeInventoryAmount(whId:string ,invId:string, delta:number){
        const body = new HttpParams;
            body.set('amount', delta)
        const url = `http://localhost:8080/warehouse/${whId}/inventory/${invId}`;
        return this.http.post(url, body);
    }

    getAllWarehouses(){
        const url = `http://localhost:8080/warehouse/all`
        return this.http.get(url);
    }
    
}