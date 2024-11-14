import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { appService } from './app.service';
import { map, Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { AsyncPipe, NgIf } from '@angular/common';
import { NgModule } from '@angular/core';
import { app } from '../../server';
import { Warehouse } from './warehouse.model';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { Inventory } from './inventory.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, FormsModule, ButtonModule, TableModule, NgIf, InputTextModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{
  formData:Warehouse = {
    id: '',
    adresas: '',
    plotasM2: ''
  };

  invForm:any = {
    whId:'',
    id: '',
    amount: 0,
  };

  id:number = 0;
 obs$!:Observable<Warehouse[]>;
 warehouses$!:Observable<any>
 inventories$!:Observable<any>;
constructor(private appService:appService){
  //this.obs$ = this.appService.getWarehouseById(this.id.toString())
  this.getAll();
}

Submit(){
  console.log(this.formData);
  this.appService.postWarehouse(this.formData).subscribe({
    next: () =>   this.getAll(),
  });
}

getById(id:string){
  this.obs$ = this.appService.getWarehouseById(this.id.toString());
}
onDelete(id:string){
  this.appService.deleteWarehouse(id).subscribe({
    next: () => this.getAll(),
  });
}

getAll(){
  this.warehouses$ = this.appService.getAllWarehouses().pipe(
    map((value) => (value && Array.isArray(value) ? value : []))
  );
  //this.warehouses$.subscribe(console.log)
}

getInventories(id:string){
  return this.appService.getInventories(id).pipe(
    map((value) => (value && Array.isArray(value) ? value : []))
  );
}

onExpand(id:string){
  this.getInventories(id).subscribe(console.log);
}

onRegisterInventory(wh:string, form:any){
  return this.appService.putInventory(wh, form).subscribe();
}

invSubmit(){
  console.log(this.invForm.whId);
  this.onRegisterInventory(this.invForm.whId, this.invForm);
}

  

}
