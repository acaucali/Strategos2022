import { Component, OnInit } from '@angular/core';
import swal from 'sweetalert2';
import { Procesos } from '../configuracion/model/procesos';
import { Arbol } from '../configuracion/model-util/arbolriesgo';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { ProcesosService } from '../configuracion/services/procesos.service';

@Component({
  selector: 'app-procesos',
  templateUrl: './procesos.component.html',
  styleUrls: ['./procesos.component.css']
})
export class ProcesosComponent implements OnInit {

  nodoSeleccionado: Arbol;
  procesoSeleccionado: Procesos;
  public ver: boolean=false;
  public arbol: any[];
  public habilitar: boolean=false;


  public expandedKeys: any[] = ['0', '1'];
  public selectedKeys: any[] = ['0'];

  

  public hasChildren = (item: any) => item.items && item.items.length > 0;
  public fetchChildren = (item: any) => of(item.items);

  constructor(private procesosService: ProcesosService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    
    this.activatedRoute.paramMap.subscribe( params =>{
      this.procesosService.getProcesosArbol().subscribe(response => this.arbol = response);       
    });

  }

  eliminarProceso(id: string): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el proceso?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.procesosService.deleteProceso(parseInt(id)).subscribe(
          response =>{
            swal.fire(
              'proceso eliminado!',
              'El proceso se ha eliminado con éxito',
              'success'
            )
          }
        )
        this.cargarArbol();
      }
    })
    
  }

  cargarArbol(){
    window.location.reload();
  }
  
  
  editarProceso(id: string){
    this.procesosService.getProceso(parseInt(id)).subscribe(response =>{ 
      this.procesoSeleccionado = response;
      this.abrirVentana();
      this.habilitarPestaña();
    }); 
    
  }

  crearProceso(id: string){

    this.procesoSeleccionado = new Procesos();
    this.procesoSeleccionado.procesoPadreId= parseInt(id);
    this.abrirVentana();
  }

  cerrarVentana(){
    this.ver= false;
  }

  abrirVentana(){
    this.ver= true;
  }

  habilitarPestaña(){
    this.habilitar= true;
  }

  deshabilitarPestaña(){
    this.habilitar = false;
  }

  regresar(){

  }

}
