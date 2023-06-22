import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import { Controles } from '../../configuracion/model/controles';
import { ControlesService } from '../../configuracion/services/controles.service';
import { ModalService } from './detalle-controles/modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-controles',
  templateUrl: './controles.component.html',
  styleUrls: ['./controles.component.css']
})
export class ControlesComponent implements OnInit {

  pageConR: number =1;

  controles: Controles[];
  paginador: any;
  controlSeleccionado: Controles;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private controlesService: ControlesService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }


  ngOnInit() {
    this.getControles();
  }

 

  /* metodos controles */

  delete(control: Controles): void{
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
        this.controlesService.delete(control.controlId).subscribe(
          response =>{
            this.getControles();
            swal.fire(
              'Control eliminado!',
              'El control ha sido eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(control: Controles){
    this.controlSeleccionado= control;
    this.modalservice.abrirModal();
  }

  crearControl(){
    this.controlSeleccionado = new Controles();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/controles/pdf"); 
  }
  
  getControles(){
    this.controles = null;
    this.elements = [];
    this.previous = [];
    this.controlesService.getControlesList().subscribe(response =>{
      this.controles = response;
      if(this.controles.length >0){
        this.controles.forEach(con =>{
          this.elements.push({controlId: con.controlId, control: con.control, procesoId: con.procesoId, controlDescripcion: con.controlDescripcion});
        });
      }
    });
  }

}
