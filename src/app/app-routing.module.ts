import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AddComponent} from './add/add.component';
import {DisplayComponent} from './display/display.component';
const routes: Routes = [
  {
    path: '',
    component:DisplayComponent
  },
  {
    path:'form/:mode',
    component:AddComponent
  },
  {
    path:'form/:mode/:id',
    component:AddComponent
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
