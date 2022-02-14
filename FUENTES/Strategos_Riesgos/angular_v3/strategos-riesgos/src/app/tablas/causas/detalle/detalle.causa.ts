import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Causas } from '../causas';
import { CausasService } from '../causas.service';
import { CausasComponent } from '../causas.component';

@Component({
  selector: 'detalle-causa',
  templateUrl: './detallecausa.component.html',
  styleUrls: ['./detallecausa.component.css']
})
export class DetalleCausa implements OnInit {
  private errores: string[];

  @Input() causa: Causas;

  titulo: string = "Datos de la causa";
  constructor(private causasService: CausasService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private causaComponent: CausasComponent
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.causasService.create(this.causa).subscribe(
      json => {
      swal.fire('Nueva Causa', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.causaComponent.getCausas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.causasService.update(this.causa).subscribe(json =>{
      swal.fire('Causa Actualizada',  `${json.mensaje}`, 'success')
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
