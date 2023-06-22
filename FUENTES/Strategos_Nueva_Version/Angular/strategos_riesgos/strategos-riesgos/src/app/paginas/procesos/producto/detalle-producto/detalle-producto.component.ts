import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Procesos } from 'src/app/paginas/configuracion/model/procesos';
import { Producto } from 'src/app/paginas/configuracion/model/producto';
import { ProductoService } from 'src/app/paginas/configuracion/services/producto.service';
import swal from 'sweetalert2';
import { ProductoComponent } from '../producto.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-producto',
  templateUrl: './detalle-producto.component.html',
  styleUrls: ['./detalle-producto.component.css']
})
export class DetalleProductoComponent implements OnInit {

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
