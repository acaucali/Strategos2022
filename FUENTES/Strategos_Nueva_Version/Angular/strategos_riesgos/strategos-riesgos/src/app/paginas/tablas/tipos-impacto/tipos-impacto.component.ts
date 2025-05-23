import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import swal from 'sweetalert2';
import { TipoImpacto } from '../../configuracion/model/tipoimpacto';
import { TipoimpactoService } from '../../configuracion/services/tipoimpacto.service';
import { ModalService } from './detalle-impacto/modal.service';

@Component({
  selector: 'app-tipos-impacto',
  templateUrl: './tipos-impacto.component.html',
  styleUrls: ['./tipos-impacto.component.css']
})
export class TiposImpactoComponent implements OnInit {

  pageImpR: number =1;
 
  impactos: TipoImpacto[];
  paginador: any;
  impactoSeleccionado: TipoImpacto;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private impactoService: TipoimpactoService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getImpactos();
  }

  
  /* metodos impacto */

  delete(impacto: TipoImpacto): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el tipo de impacto ${impacto.tipoImpacto} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.impactoService.delete(impacto.tipoImpactolId).subscribe(
          response =>{
            this.getImpactos();
            swal.fire(
              'Tipo Impacto eliminado!',
              'El impacto ha sido eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(impacto: TipoImpacto){
    this.impactoSeleccionado= impacto;
    this.modalservice.abrirModal();
  }

  crearImpacto(){
    this.impactoSeleccionado = new TipoImpacto();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/tipoimpacto/pdf"); 
  }

  getImpactos(){
    this.impactos = null;
    this.elements = [];
    this.previous = [];
    this.impactoService.getImpactosList().subscribe(response =>{
      this.impactos = response;
      if(this.impactos.length >0){
        this.impactos.forEach(imp =>{
          this.elements.push({tipoImpactolId: imp.tipoImpactolId, tipoImpacto: imp.tipoImpacto, tipoImpactoPuntaje: imp.tipoImpactoPuntaje, tipoImpactoDescripcion: imp.tipoImpactoDescripcion});
        });
      }
     
    });
  }

}
