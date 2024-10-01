import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule} from './product-routing.module';
import { ProductCreateComponent } from './product-create/product-create.component';
import { ProductEditComponent } from './product-edit/product-edit.component';
import { ProductListComponent } from './product-list/product-list.component';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatListModule } from '@angular/material/list';
import { HttpClientModule } from '@angular/common/http';


  @NgModule({
    declarations: [
      ProductCreateComponent,
      ProductEditComponent,
      ProductListComponent
    ],
    imports: [
      CommonModule,
      ProductRoutingModule,
      MatInputModule,
      MatToolbarModule,
      MatIconModule,
      MatSidenavModule,
      MatDividerModule,
      MatButtonModule,
      FormsModule,
      MatFormFieldModule,
      MatSelectModule,
      MatCardModule,
      MatSnackBarModule,
      MatListModule,
      HttpClientModule
    ]
})
export class ProductModule { }
