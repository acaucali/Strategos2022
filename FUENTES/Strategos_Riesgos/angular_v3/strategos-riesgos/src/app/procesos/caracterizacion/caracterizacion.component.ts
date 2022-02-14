
import { Caracterizacion } from './caracterizacion';
import { CaracterizacionService } from './caracterizacion.service';
import { ModalService } from './modal/modal.service';
import { ActivatedRoute } from '@angular/router';
import { ProcesosService } from '../procesos.service';
import { ProcesosComponent } from '../procesos.component';
import { Procesos } from '../procesos';
import swal from 'sweetalert2';
import { ViewChild, OnInit, AfterViewInit, Component, Input, ChangeDetectorRef } from '@angular/core';
import { MdbTablePaginationComponent, MdbTableService } from 'angular-bootstrap-md';


@Component({
  selector: 'app-caracterizacion',
  templateUrl: './caracterizacion.component.html',
  styleUrls: ['./caracterizacion.component.css']
})
export class CaracterizacionComponent implements OnInit {


  pageCar: number =1;
  procedimientos: Caracterizacion[]; 
  paginador: any;
  procedimientoSeleccionado: Caracterizacion=new Caracterizacion();

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  @Input() proceso: Procesos;
  
  constructor(private procedimientoService: CaracterizacionService,
    private modalservice: ModalService, 
    private activatedRoute: ActivatedRoute, private procesosService: ProcesosService, private procesosComponent: ProcesosComponent) { }

  ngOnInit() {
    if(this.proceso.procesoCaracterizacion != null){
      this.getProcedimientos();
    }
  }

  /* metodos caracterizacion */
  
  delete(procedimiento: Caracterizacion): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el procedimiento ${procedimiento.procedimientoNombre} ?`,
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
        this.procedimientoService.delete(procedimiento.procedimientoId).subscribe(
          response =>{
            this.getProcedimientos()
            swal.fire(
              'Procedimiento eliminado!',
              'El procedimiento se ha eliminado con éxito',
              'success'
            )
          }
        )
      }
    })
  }
  

  
  editarProcedimiento(procedimiento: Caracterizacion){
    this.procedimientoSeleccionado= procedimiento;
    this.modalservice.abrirModal();
  }

  crearProcedimiento(){
    this.procedimientoSeleccionado = new Caracterizacion();
    this.modalservice.abrirModal();
  }

  getProcedimientos(){
    this.procedimientos= null;
    this.elements = [];
    this.previous = [];
    this.procesosService.getProcedimientos(this.proceso.procesosId).subscribe(response => {
      this.procedimientos = response;
      if(this.procedimientos.length >0){
        this.procedimientos.forEach(pro =>{
          this.elements.push({procedimientoId: pro.procedimientoId, procedimientoNombre: pro.procedimientoNombre, procedimientoCodigo: pro.procedimientoCodigo, procedimientoObjetivo: pro.procedimientoObjetivo,
          procedimientoDocumento: pro.procedimientoDocumento, proceso: pro.proceso
          });
        });
      }
    });
  }

}
