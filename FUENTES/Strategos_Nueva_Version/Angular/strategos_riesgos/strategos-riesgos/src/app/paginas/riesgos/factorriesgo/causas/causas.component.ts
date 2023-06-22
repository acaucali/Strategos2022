import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CausaFactor } from 'src/app/paginas/configuracion/model/causaFactor';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { CausaFactorService } from 'src/app/paginas/configuracion/services/causa.factor.service';
import { FactorService } from 'src/app/paginas/configuracion/services/factor.service';
import swal from 'sweetalert2';
import { DetalleFactor } from '../detalle/detallefactor.control';
import { ModalService } from './detalle-causas/modal.service';

@Component({
  selector: 'app-causas-factores',
  templateUrl: './causas.component.html',
  styleUrls: ['./causas.component.css']
})
export class CausasComponent implements OnInit {

  page: number =1;

  public promedio: number=0;
  causas: CausaFactor[]; 
  causaSeleccionada: CausaFactor;
  @Input() factor: FactorRiesgo;

  

  constructor( private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private detalleFactorComponent: DetalleFactor,
    private factorService: FactorService, private causaFactorService: CausaFactorService
    ) { }

  ngOnInit() {

    
    this.factor = this.detalleFactorComponent.factor;
    if(this.factor.causaRiesgo != null){
      this.getCausas();
    }
  }

  

  crearCausa(){
    this.causaSeleccionada = new CausaFactor();
    this.modalservice.abrirModal();
  }

  getCausas(){
    this.causas = null;
    this.factorService.getCausas(this.factor.factorRiesgoId).subscribe(response =>{
    this.causas = response
    if(this.causas.length >0){
      this.calcularPromedio(this.causas); 
      
    }
    });
  }

  delete(causa: CausaFactor): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la causa ${causa.causa} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.causaFactorService.delete(causa.causaRiesgoId).subscribe(
          response =>{
            this.getCausas();
            swal.fire(
              'Causa eliminada!',
              'La causa se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }
  
  abrirModal(causa: CausaFactor){
    this.causaSeleccionada= causa;
    this.modalservice.abrirModal();
  }

  calcularPromedio(causas: CausaFactor[]){
    let suma =0;
    let cont =0;
    let prom =0;
    if(causas.length >0){
      causas.forEach(cau =>{
        suma += cau.probabilidadId;
        cont ++;
      });
      let prom= (suma/cont);
      this.promedio = Math.round(prom);
      this.detalleFactorComponent.factor.probabilidad = Math.round(prom);
      if(this.detalleFactorComponent.factor.probabilidad >0 && this.detalleFactorComponent.factor.impacto >0){
        this.detalleFactorComponent.factor.severidad = this.detalleFactorComponent.calcularSeveridad(Math.round(prom), this.detalleFactorComponent.factor.impacto);
        this.detalleFactorComponent.calcularAlerta(this.detalleFactorComponent.factor.severidad);
      }
      if(this.detalleFactorComponent.factor.probabilidad >0 && this.detalleFactorComponent.factor.impacto >0 && this.factor.controlId){
        this.detalleFactorComponent.factor.riesgoResidual= this.detalleFactorComponent.calcularRiesgo(Math.round(prom), this.detalleFactorComponent.factor.impacto, this.detalleFactorComponent.factor.controlId);
      }
    }
  }

}
