import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { appService } from './app.service';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { AsyncPipe, NgIf } from '@angular/common';
import { NgModule } from '@angular/core';
import { app } from '../../server';
import { Warehouse } from './warehouse.model';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';

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
  id:number = 0;
 obs$!:Observable<Warehouse[]>;
constructor(private appService:appService){
  //this.obs$ = this.appService.getWarehouseById(this.id.toString())
}

Submit(){
  console.log(this.formData);
  this.appService.postWarehouse(this.formData).subscribe();
}

getById(id:string){
  this.obs$ = this.appService.getWarehouseById(this.id.toString());
}
onDelete(id:string){
  this.appService.deleteWarehouse(id).subscribe();
  this.getById(id);
}

  

}
