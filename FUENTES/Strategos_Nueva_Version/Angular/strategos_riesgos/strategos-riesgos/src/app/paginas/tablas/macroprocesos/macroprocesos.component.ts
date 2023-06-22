import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import { MacroProcesos } from '../../configuracion/model/macroprocesos';
import { MacroprocesosService } from '../../configuracion/services/macroprocesos.service';
import { ModalService } from './detalle-macro/modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-macroprocesos',
  templateUrl: './macroprocesos.component.html',
  styleUrls: ['./macroprocesos.component.css']
})
export class MacroprocesosComponent implements OnInit {

  pageMacR: number =1;

  macros: MacroProcesos[];
  paginador: any;
  macroProcesoSeleccionado: MacroProcesos;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private macroService: MacroprocesosService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getMacros();
  }

  

  /* metodos macroprocesos */

  delete(macro: MacroProcesos): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el Macroproceso ${macro.macroProceso} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.macroService.delete(macro.macroProcesoId).subscribe(
          response =>{
            this.getMacros();
            swal.fire(
              'Macroproceso eliminado!',
              'El Macroproceso se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(macro: MacroProcesos){
    this.macroProcesoSeleccionado= macro;
    this.modalservice.abrirModal();
  }

  crearMacro(){
    this.macroProcesoSeleccionado = new MacroProcesos();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/macroproceso/pdf"); 
  }

  getMacros(){
    this.macros = null;
    this.elements = [];
    this.previous = [];
    this.macroService.getMacrosList().subscribe(response =>{
      this.macros = response;
      if(this.macros.length >0){
        this.macros.forEach(mac =>{
          this.elements.push({macroProcesoId: mac.macroProcesoId, macroProceso: mac.macroProceso, macroProcesoDescripcion: mac.macroProcesoDescripcion, macroProcesoCodigo: mac.macroProcesoCodigo});
        });
      }
     
    });
  }

}
