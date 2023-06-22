import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EfectoFactor } from 'src/app/paginas/configuracion/model/efectoFactor';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { FactorService } from 'src/app/paginas/configuracion/services/factor.service';
import swal from 'sweetalert2';
import { DetalleFactor } from '../detalle/detallefactor.control';
import { ModalService } from './detalle-efecto/modal.service';
import { efectoFactorService } from 'src/app/paginas/configuracion/services/efector.service';

@Component({
  selector: 'app-efectos-factor',
  templateUrl: './efectos.component.html',
  styleUrls: ['./efectos.component.css']
})
export class EfectosComponent implements OnInit {

  pageEfecto: number =1;

  public promedio: number=0;
  efectos: EfectoFactor[]; 
  efectoSeleccionado: EfectoFactor;
  @Input() factor: FactorRiesgo;

  displayedColumns: string[] = ['Acciones', 'efecto', 'impacto'];

  

  constructor( private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private detalleFactorComponent: DetalleFactor,
    private factorService: FactorService, private efectoFactorService: efectoFactorService) { }

  ngOnInit() {
    this.factor = this.detalleFactorComponent.factor;
    if(this.factor.efectoRiesgo != null){
      this.getEfectos();
    }
  }

  
  /* metodos efectos */

  crearEfecto(){
    this.efectoSeleccionado = new EfectoFactor();
    this.modalservice.abrirModal();
  }

  getEfectos(){
    this.efectos = null;
    this.factorService.getEfectos(this.factor.factorRiesgoId).subscribe(response =>{
    this.efectos = response;
    if(this.efectos.length >0){
      this.calcularPromedio(this.efectos); 
    }
    });
  }

  delete(efecto: EfectoFactor): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el factor ${efecto.efectoNombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.efectoFactorService.delete(efecto.efectoRiesgoId).subscribe(
          response =>{
            this.getEfectos();
            swal.fire(
              'Efecto eliminado!',
              'El efecto se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(efecto: EfectoFactor){
    this.efectoSeleccionado= efecto;
    this.modalservice.abrirModal();
  }

  calcularPromedio(efectos: EfectoFactor[]){
    let suma =0;
    let cont =0;
    let prom =0;
    if(efectos.length >0){
      efectos.forEach(efe =>{
        suma += efe.tipoImpactoId;
        cont ++;
      });
      let prom= (suma/cont);
      this.promedio = Math.round(prom);
      this.detalleFactorComponent.factor.impacto = Math.round(prom);
      if(this.detalleFactorComponent.factor.probabilidad >0 && this.detalleFactorComponent.factor.impacto >0){
        this.detalleFactorComponent.factor.severidad = this.detalleFactorComponent.calcularSeveridad( this.detalleFactorComponent.factor.probabilidad, Math.round(prom));
        this.detalleFactorComponent.calcularAlerta(this.detalleFactorComponent.factor.severidad);
      }
      if(this.detalleFactorComponent.factor.probabilidad >0 && this.detalleFactorComponent.factor.impacto >0 && this.factor.controlId){
        this.detalleFactorComponent.factor.riesgoResidual= this.detalleFactorComponent.calcularRiesgo(this.detalleFactorComponent.factor.probabilidad, Math.round(prom), this.detalleFactorComponent.factor.controlId);
      }
    }
  }

}
