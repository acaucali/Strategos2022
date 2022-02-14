import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { HttpClientModule } from '@angular/common/http'; 
import { Router, ActivatedRoute } from '@angular/router';
import { Producto } from '../producto';
import { ProductoService } from '../producto.service';
import { Procesos } from '../../procesos';
import { ProductoComponent } from '../producto.component';

@Component({
  selector: 'detalle-producto',
  templateUrl: './detalle.producto.html',
  styleUrls: ['./detalle.producto.css']
})
export class DetalleProducto implements OnInit {
  private errores: string[];
 

  @Input() producto: Producto;

  @Input() proceso: Procesos;

  public titulo: string = "Datos del producto";
  
  constructor(private productoService: ProductoService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private productoComponent: ProductoComponent
   ) { }

  ngOnInit() {
  }

  
  
  create(): void{
    this.productoService.create(this.producto, this.proceso).subscribe(
      json => {
      swal.fire('Nuevo Producto', `${json.mensaje}`, 'success');
      this.cerrarModal();  
      this.productoComponent.productos= null;
      this.productoComponent.getProductos();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  
  update(): void{
    this.productoService.update(this.producto).subscribe(json =>{
      swal.fire('Producto Actualizado',  `${json.mensaje}`, 'success')
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
