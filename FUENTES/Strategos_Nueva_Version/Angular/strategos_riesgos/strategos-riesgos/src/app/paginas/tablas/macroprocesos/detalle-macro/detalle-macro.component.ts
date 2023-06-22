import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MacroProcesos } from 'src/app/paginas/configuracion/model/macroprocesos';
import { MacroprocesosService } from 'src/app/paginas/configuracion/services/macroprocesos.service';
import swal from 'sweetalert2';
import { MacroprocesosComponent } from '../macroprocesos.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-macro',
  templateUrl: './detalle-macro.component.html',
  styleUrls: ['./detalle-macro.component.css']
})
export class DetalleMacroComponent implements OnInit {

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
