import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ControlFactor } from 'src/app/paginas/configuracion/model/controlFactor';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { ControlFactorService } from 'src/app/paginas/configuracion/services/controles.factor.service';
import { FactorService } from 'src/app/paginas/configuracion/services/factor.service';
import swal from 'sweetalert2';
import { DetalleFactor } from '../detalle/detallefactor.control';
import { ModalService } from './detalle-controles/modal.service';

@Component({
  selector: 'app-controles-factor',
  templateUrl: './controles.component.html',
  styleUrls: ['./controles.component.css']
})
export class ControlesComponent implements OnInit {

  pageControl: number =1;

  
  controles: ControlFactor[];
  controlSeleccionado: ControlFactor;
  @Input() factor: FactorRiesgo;
  public promedio: number=0; 

  constructor(private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private detalleFactorComponent: DetalleFactor,
    private factorService: FactorService, private controlFactorService: ControlFactorService,
    
    ) { }

  ngOnInit() {

    this.factor = this.detalleFactorComponent.factor;
    if(this.factor.controlesRiesgo != null){
      this.getControles();
    }
  }

  
  crearControl(){
    this.controlSeleccionado = new ControlFactor();
    this.modalservice.abrirModal();
  }

  getControles(){
    this.controles = null;
    this.factorService.getControles(this.factor.factorRiesgoId).subscribe(response =>{
    this.controles = response
    if(this.controles.length >0){
      this.calcularPromedio(this.controles); 
    }
    });
  }

  delete(control: ControlFactor): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el control ${control.control} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.controlFactorService.delete(control.controlRiesgoId).subscribe(
          response =>{
            this.getControles();
            swal.fire(
              'Control eliminado!',
              'El control se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }
  
  abrirModal(control: ControlFactor){
    this.controlSeleccionado= control;
    this.modalservice.abrirModal();
  }

  calcularPromedio(controles: ControlFactor[]){
    let suma =0;
    let cont =0;
    let prom =0;
    if(controles.length >0){
      controles.forEach(con =>{
        suma += con.efectividadId;
        cont ++;
      });
      let prom= (suma/cont);
      this.promedio = Math.round(prom);
      this.detalleFactorComponent.factor.controlId = Math.round(prom);
      if(this.detalleFactorComponent.factor.probabilidad >0 && this.detalleFactorComponent.factor.impacto >0){
        this.detalleFactorComponent.factor.severidad = this.detalleFactorComponent.calcularSeveridad(this.detalleFactorComponent.factor.probabilidad, this.detalleFactorComponent.factor.impacto);
        this.detalleFactorComponent.calcularAlerta(this.detalleFactorComponent.factor.severidad);
      }
      if(this.detalleFactorComponent.factor.probabilidad >0 && this.detalleFactorComponent.factor.impacto >0 && this.factor.controlId){
        this.detalleFactorComponent.factor.riesgoResidual= this.detalleFactorComponent.calcularRiesgo(this.detalleFactorComponent.factor.probabilidad, this.detalleFactorComponent.factor.impacto, Math.round(prom));
      }
    }
  }

}
