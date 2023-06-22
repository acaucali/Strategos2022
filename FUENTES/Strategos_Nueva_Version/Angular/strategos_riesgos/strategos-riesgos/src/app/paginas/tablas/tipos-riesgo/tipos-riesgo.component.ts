import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import swal from 'sweetalert2';
import { TipoRiesgo } from '../../configuracion/model/tiporiesgo';
import { TiporiesgoService } from '../../configuracion/services/tiporiesgo.service';
import { ModalService } from './detalle-riesgo/modal.service';

@Component({
  selector: 'app-tipos-riesgo',
  templateUrl: './tipos-riesgo.component.html',
  styleUrls: ['./tipos-riesgo.component.css']
})
export class TiposRiesgoComponent implements OnInit {

  pageTipR: number =1;

  riesgos: TipoRiesgo[]; 
  paginador: any;
  riesgoSeleccionado: TipoRiesgo;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private riesgoService: TiporiesgoService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getTipos();
  }

  

  /* metodos tipo */

  delete(riesgo: TipoRiesgo): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el tipo de riesgo ${riesgo.tipoRiesgo} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.riesgoService.delete(riesgo.tipoRiesgoId).subscribe(
          response =>{
            this.getTipos();
            swal.fire(
              'Tipo de Riesgo eliminado!',
              'El Tipo de Impacto ha sido eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(riesgo: TipoRiesgo){
    this.riesgoSeleccionado= riesgo;
    this.modalservice.abrirModal();
   
  }

  crearRiesgo(){
    this.riesgoSeleccionado = new TipoRiesgo();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"/api/tablas/tiporiesgo/pdf"); 
  }

  getTipos(){
    this.riesgos = null;
    this.elements = [];
    this.previous = [];
    this.riesgoService.getRiesgosList().subscribe(response =>{
      this.riesgos = response;
      if(this.riesgos.length >0){
        this.riesgos.forEach(rie =>{
          this.elements.push({tipoRiesgoId: rie.tipoRiesgoId, tipoRiesgo: rie.tipoRiesgo});
        });
      }
    
    });
  }

}
