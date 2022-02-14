import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { RespuestaService } from 'src/app/tablas/respuesta/respuesta.service';
import { TiporiesgoService } from 'src/app/tablas/tipos-riesgo/tiporiesgo.service';

import { Respuesta } from 'src/app/tablas/respuesta/respuesta';
import { TipoRiesgo } from 'src/app/tablas/tipos-riesgo/tiporiesgo';
import { ActivatedRoute } from '@angular/router';
import { DetalleFactor } from '../detallefactor.control';

import swal from 'sweetalert2';
import { FactorRiesgo } from '../../factor';
import { Ejercicios } from 'src/app/riesgos/ejercicios/ejercicios';
import { FactorriesgoComponent } from '../../factorriesgo.component';
import { FactorService } from '../../factor.service';
import { ModalService } from '../modal.service';


@Component({
  selector: 'app-form-factor',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormFactorComponent implements OnInit{

  
  public respuestas: Respuesta[];
  public tipos: TipoRiesgo[];
  public factor: FactorRiesgo;
  public ejercicio: Ejercicios;
  errores: string[];
  private accion: string;


  constructor(private respuestasService: RespuestaService, private tipoRiesgoService: TiporiesgoService, public modalService: ModalService,
    private factorService: FactorService, private activatedRoute: ActivatedRoute, private detalleFactorComponent: DetalleFactor, 
    private factorComponent: FactorriesgoComponent
  
    ) { }

  ngOnInit() {
    this.factor = this.detalleFactorComponent.factor;
    if(this.factor.probabilidad >0 && this.factor.impacto >0){
      this.factor.severidad = this.factor.probabilidad * this.factor.impacto;
    }
    if(this.factor.probabilidad >0 && this.factor.impacto >0 && this.factor.controlId){
      this.factor.riesgoResidual = (this.factor.probabilidad - this.factor.controlId)*this.factor.impacto; 
    }
    this.ejercicio = this.factorComponent.ejercicio;
    this.respuestasService.getRespuestasList().subscribe(response => this.respuestas= response);
    this.tipoRiesgoService.getRiesgosList().subscribe(response => this.tipos= response);
  }

  

  
  
  create(): void{
    
    this.factorService.create(this.factor, this.ejercicio, this.factorComponent.proceso).subscribe(
      json => {
      swal.fire('Nuevo Factor de Riesgo', `${json.mensaje}`, 'success');
      this.factorComponent.cargarDatosGrafico();
      this.factor= json.factor;
      this.factorComponent.factorSeleccionado= json.factor;
      this.detalleFactorComponent.factorSeleccionado = json.factor;
      this.detalleFactorComponent.habilitarPestaña();
      this.accion = "crear";

    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }


 update(): void{
  this.factorService.update(this.factor).subscribe(json =>{
    swal.fire('Factor Actualizado',  `${json.mensaje}`, 'success');
    this.factorComponent.cargarDatosGrafico();
    this.cerrarVentana();
  },
  err =>{
    this.errores = err.error.errors as string[];
    console.error('Código error: '+err.status);
    console.error(err.error.errors);
  }
  );
 }
  
  cerrarVentana(){
  if(this.accion !=null && this.accion == "crear"){
    this.factorComponent.cargarFactores();
    this.detalleFactorComponent.cerrarModal();
  }else{
    this.detalleFactorComponent.deshabilitarPestaña();
    this.detalleFactorComponent.cerrarModal();
  }


 }
}
