import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { of } from 'rxjs';
import { Arbol } from '../../configuracion/model-util/arbol';
import { PerspectivaService } from '../../configuracion/services/perspectiva.service';
import { PlanesComponent } from '../planes.component';
import { Plan } from '../../configuracion/model/plan';
import { Perspectiva } from '../../configuracion/model/perspectiva';
import { ModalService } from './detalle-perspectiva/modal.service';
import Swal from 'sweetalert2';
import { IndicadoresComponent } from '../indicadores/indicadores.component';
import { IniciativasComponent } from '../iniciativas/iniciativas.component';


@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.css']
})
export class PlanComponent implements OnInit, AfterViewInit {

  @ViewChild("treeview") treeview: ElementRef;

  nodoSeleccionado: Arbol;
  public ver: boolean=false;
  public arbol: any[];
  public habilitar: boolean=false;
  nombrePerspectiva: any;

  public expandedKeys: any[] = ['0', '1'];
  public selectedKeys: any[] = ['0'];

  public planId: any;  
 
  perspectivaSeleccionada: Perspectiva;

  public hasChildren = (item: any) => item.items && item.items.length > 0;
  public fetchChildren = (item: any) => of(item.items);

  

  constructor(private perspectivaService: PerspectivaService, public modalservice: ModalService, public componentePadre: PlanesComponent) { }

  ngOnInit(): void {   
    
    this.perspectivaService.getPerspectivasArbol(localStorage.getItem('planId')).subscribe(response => this.arbol = response);
  }

  ngAfterViewInit() {
    
  }

  crearPerspectiva(id: string){
    if(id != '0'){
      this.perspectivaSeleccionada = new Perspectiva();
      this.perspectivaSeleccionada.padreId = Number(id);    
      this.modalservice.abrirModal();    
    }
    console.log(this.perspectivaSeleccionada);
  }

  editarPerspectiva(id: string){
    if(id != '0'){
      this.perspectivaService.getPerspectiva(Number(id)).subscribe(response =>{
        this.perspectivaSeleccionada = response;
        this.modalservice.abrirModal();
      });
    }    
  }

  eliminarPerspectiva(id: string){
   
    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la perspectiva?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.perspectivaService.delete(Number(id)).subscribe(
          response =>{
            this.getPerspectivas();
            Swal.fire(
              'Perspectiva eliminada!',
              'La perspectiva ha sido eliminada con éxito',
              'success'
            )
          }
        )
      }
    })

  }

  onClick(id) {
    localStorage.setItem('objetivoId', id);
    this.perspectivaService.getPerspectiva(id).subscribe(response => localStorage.setItem('nombreObjetivo', response.nombre.toString()));
    this.componentePadre.getIndicadoresIniciativas();    
  }

  getPerspectivas(){
    
    this.arbol = [];
    this.perspectivaService.getPerspectivasArbol(localStorage.getItem('planId')).subscribe(response => this.arbol = response);
    
  }

}
