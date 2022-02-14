import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MacroProcesos } from '../macroprocesos';
import { MacroprocesosService } from '../macroprocesos.service';
import { MacroprocesosComponent } from '../macroprocesos.component';


@Component({
  selector: 'detalle-macro',
  templateUrl: './detallemacroproceso.component.html',
  styleUrls: ['./detallemacroproceso.component.css']
})
export class DetalleMacro implements OnInit {
  private errores: string[];

  @Input() macro: MacroProcesos;

  titulo: string = "Datos del Macroproceso";
  constructor(private macroService: MacroprocesosService, private router: Router, 
    private activatedRoute: ActivatedRoute, private macroComponent: MacroprocesosComponent,
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.macroService.create(this.macro).subscribe(
      json => {
      swal.fire('Nuevo Macroproceso', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.macroComponent.getMacros();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.macroService.update(this.macro).subscribe(json =>{
      swal.fire('Macroproceso Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }
}
