import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { NgxPaginationModule } from 'ngx-pagination';

import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import {MatSelectModule} from '@angular/material/select';
import { InputsModule } from '@progress/kendo-angular-inputs';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';

import { TreeViewModule } from '@progress/kendo-angular-treeview';
import { CausasComponent } from './causas/causas.component';
import { DetalleCausasComponent } from './causas/detalle-causas/detalle-causas.component';
import { ControlesComponent } from './controles/controles.component';
import { DetalleControlesComponent } from './controles/detalle-controles/detalle-controles.component';
import { DetalleFactor } from './detalle/detallefactor.control';
import { FormFactorComponent } from './detalle/form/form.factor.component';
import { DetalleEfectoComponent } from './efectos/detalle-efecto/detalle-efecto.component';
import { EfectosComponent } from './efectos/efectos.component';
import { FactorRoutingModule } from './factor-routing.module';
import { FactorriesgoComponent } from './factorriesgo.component';





@NgModule({
  declarations: [FactorriesgoComponent, EfectosComponent, CausasComponent, ControlesComponent, DetalleEfectoComponent, DetalleControlesComponent, DetalleCausasComponent
  , DetalleFactor, FormFactorComponent],
  imports: [
    CommonModule, FactorRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule,  NgMultiSelectDropDownModule.forRoot(),
    MatSelectModule, DropDownsModule, InputsModule, TreeViewModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class FactorModule { }
