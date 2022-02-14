import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { FactorRiesgo } from '../factor';
import { FactorService } from '../factor.service';
import { FactorriesgoComponent } from '../factorriesgo.component';
import { Ejercicios } from '../../ejercicios/ejercicios';
import { CalificacionesService } from 'src/app/tablas/calificaciones/calificaciones.service';
import { Calificaciones } from 'src/app/tablas/calificaciones/calificaciones';

@Component({
  selector: 'detalle-factor',
  templateUrl: './detallefactor.component.html',
  styleUrls: ['./detallefactor.component.css']
})
export class DetalleFactor implements OnInit{

  titulo: string = "Datos del Factor de Riesgo";
  @Input() factor: FactorRiesgo = new FactorRiesgo(); 
  factorSeleccionado: FactorRiesgo; 

  public habilitar: boolean=false;
  public calificaciones: Calificaciones[];
  

  private errores: string[];

  constructor(private factorService: FactorService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private factorComponent: FactorriesgoComponent,
    private calificacionService: CalificacionesService) { }

  ngOnInit() {
    if(this.factorComponent.accion != null && this.factorComponent.accion == "update"){
      this.habilitarPestaña();
    }
    
  }

  calcularSeveridad(pro: number, imp: number){
    let res=0;
    if(pro>0 && imp>0){
      res = pro * imp;
    }
    return Math.round(res);
  }
  calcularRiesgo(pro: number, imp: number, con: number){
    let res=0;
    if(pro >0 && imp >0 && con>0){
      res = ((pro *imp)-con); 
      if(res <0){
        res =0;
      }
    }
    return Math.round(res);
  }

  calcularAlerta(sev: number){
    let alerta="";
    this.calificacionService.getCalificacionesList().subscribe(response =>{
      this.calificaciones = response;
      if(this.calificaciones.length >0){
      this.calificaciones.forEach(cal =>{
        if(sev>=cal.calificacionesRiesgoMinimo && sev<cal.calificacionesRiesgoMaximo){
          alerta=cal.calificacionesRiesgoColor;
          this.factor.alerta = alerta;
        }
      });
    }
    });
    
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  habilitarPestaña(){
    this.habilitar= true;
  }

  deshabilitarPestaña(){
    this.habilitar = false;
  }
}
