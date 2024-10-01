import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductCreateComponent } from './product-create/product-create.component';
import { ProductEditComponent } from './product-edit/product-edit.component';
import { AuthGuard } from '../guard/authGuard';

const routes: Routes = [
  { path: 'list', component: ProductListComponent },
  { path: 'create', component: ProductCreateComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'edit/:id', component: ProductEditComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }