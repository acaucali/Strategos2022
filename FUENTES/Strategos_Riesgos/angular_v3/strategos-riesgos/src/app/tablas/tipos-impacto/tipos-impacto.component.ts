import { Component, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { TipoImpacto } from './tipoimpacto';
import { TipoimpactoService } from './tipoimpacto.service';
import { ModalService } from './detalle/modal.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { MdbTableService, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-tipos-impacto',
  templateUrl: './tipos-impacto.component.html',
  styleUrls: ['./impactos.component.css']
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
      type: 'warning',
      showCancelButton: true,
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
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
