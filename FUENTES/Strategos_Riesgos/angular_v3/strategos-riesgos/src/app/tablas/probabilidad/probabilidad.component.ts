import { Component, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { Probabilidad } from './probabilidad';
import { ProbabilidadService } from './probabilidad.service';
import { ModalService } from './detalle/modal.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { MdbTableService, MdbTablePaginationComponent } from 'angular-bootstrap-md';
import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-probabilidad',
  templateUrl: './probabilidad.component.html',
  styleUrls: ['./probabilidad.component.css']
})
export class ProbabilidadComponent implements OnInit {

  pageProR: number =1;

  probabilidades: Probabilidad[];
  paginador: any;
  probabilidadSeleccionado: Probabilidad; 

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private probabilidadService: ProbabilidadService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getProbabilidad();
  }

  

  /* metodos probabilidad */

  delete(probabilidad: Probabilidad): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la probabilidad ${probabilidad.probabilidadNombre} ?`,
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
        this.probabilidadService.delete(probabilidad.probabilidadId).subscribe(
          response =>{
            this.getProbabilidad();
            swal.fire(
              'Probabilidad eliminada!',
              'La probabildad ha sido eliminada con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(probabilidad: Probabilidad){
    this.probabilidadSeleccionado= probabilidad;
    this.modalservice.abrirModal();
  }

  crearProbabilidad(){
    this.probabilidadSeleccionado = new Probabilidad();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/probabilidad/pdf"); 
  }

  getProbabilidad(){
    this.probabilidades = null;
    this.elements = [];
    this.previous = [];
    this.probabilidadService.getProbabilidadesList().subscribe(response =>{
      this.probabilidades = response;
      if(this.probabilidades.length >0){
        this.probabilidades.forEach(pro =>{
          this.elements.push({probabilidadId: pro.probabilidadId, probabilidadNombre: pro.probabilidadNombre, probabilidadPuntaje: pro.probabilidadPuntaje, probabilidadDescripcion: pro.probabilidadDescripcion});
        });
      }
      
    });
  }
}
