import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';

import { HttpClientModule } from '@angular/common/http'; 
import { Router, ActivatedRoute } from '@angular/router';
import { ModalControlFactorService } from './modalControl.service';

import { FactorRiesgo } from '../../factor';
import { Causas } from 'src/app/tablas/causas/causas';
import { CausasService } from 'src/app/tablas/causas/causas.service';
import { ProbabilidadService } from 'src/app/tablas/probabilidad/probabilidad.service';
import { Probabilidad } from 'src/app/tablas/probabilidad/probabilidad';

import { ControlFactor } from '../controlFactor';
import { Controles } from 'src/app/tablas/controles/controles';
import { EfectividadControles } from 'src/app/tablas/efectividad/efectividad';
import { ControlesFactorComponent } from '../controlesFactor.component';
import { ControlFactorService } from '../controles.factor.service';
import { EfectividadService } from 'src/app/tablas/efectividad/efectividad.service';
import { ControlesService } from 'src/app/tablas/controles/controles.service';
import { FactorriesgoComponent } from '../../factorriesgo.component';


@Component({
  selector: 'detalle-control-factor',
  templateUrl: './detalle.control.factor.html',
  styleUrls: ['./detalle.control.factor.css']
})
export class DetalleControlFactor implements OnInit {
  private errores: string[];
  public controlRiesgo: Controles[];
  public efectividades: EfectividadControles[];
  public controlesString: Array<string> = [];
  public controlRi: Controles= new Controles();



  @Input() control: ControlFactor;

  @Input() factor: FactorRiesgo;

  public titulo: string = "Datos control factor";
  
  constructor( private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalControlFactorService, private controlRiesgoService: ControlesService,
    private controlFactorService: ControlFactorService,
    private efectividadService: EfectividadService, private controlFactorComponent: ControlesFactorComponent, private factorComponent: FactorriesgoComponent
   ) { }

  ngOnInit() {
    let cont: number = 0;
    this.controlRiesgoService.getControlesListProceso(this.factorComponent.proceso.procesosId).subscribe(response =>{
      this.controlRiesgo = response;
      if(this.controlRiesgo.length >0){
      this.controlRiesgo.forEach(con =>{
        this.controlesString.push(con.control);
        cont ++;
      });
    }
    });
    this.efectividadService.getEfectividadList().subscribe(response => this.efectividades = response);
  }

  
  
  create(): void{
    this.controlRi.control = this.control.control;
    this.controlRi.controlDescripcion = this.control.descripcionControl;
    this.controlRi.procesoId = this.factorComponent.proceso.procesosId;

    this.controlFactorService.create(this.control, this.factor).subscribe(
      json => {
      swal.fire('Nuevo Control', `${json.mensaje}`, 'success');
      this.cerrarModal();  
      this.controlFactorComponent.controles= null;
      this.controlFactorComponent.getControles();
      this.controlRiesgoService.create(this.controlRi).subscribe(response => { console.log(response.mensaje) });  
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  
  

  update(): void{
    this.controlFactorService.update(this.control).subscribe(json =>{
      swal.fire('Control Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
      this.controlFactorComponent.controles= null;
      this.controlFactorComponent.getControles();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  

  public listItems: Array<string> = this.controlesString;

  cerrarModal(){
    this.modalservice.cerrarModal();
  }
}
