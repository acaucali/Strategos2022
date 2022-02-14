import { Component, OnInit, Input, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { Producto } from './producto';
import { ProductoService } from './producto.service';
import { ModalService } from './modal/modal.service';
import { ActivatedRoute } from '@angular/router';
import { Procesos } from '../procesos';
import { ProcesosService } from '../procesos.service';
import { ProcesosComponent } from '../procesos.component';
import swal from 'sweetalert2';
import { HttpClientModule } from '@angular/common/http';
import { MdbTableService, MdbTablePaginationComponent } from 'angular-bootstrap-md';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css']
})
export class ProductoComponent implements OnInit {

  
  pagePro: number =1;

  productos: Producto[];
  paginador: any; 
  productoSeleccionado: Producto;

  elements: any = [];
  previous: any = [];

  firstItemIndex1;
  lastItemIndex1;

  @Input() proceso: Procesos;

  constructor( private productoService: ProductoService,
    private modalservice: ModalService, 
    private activatedRoute: ActivatedRoute, private procesosService: ProcesosService, private procesosComponent: ProcesosComponent) { }

  ngOnInit() {      
    if(this.proceso.procesoProducto != null){
      this.getProductos(); 
    }
  }

  
  /* metodos producto */

  delete(producto: Producto): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el producto ${producto.procesoProductoServicio} ?`,
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
        
        this.productoService.delete(producto.procesoProductoId).subscribe(
          response =>{
            this.getProductos();
            swal.fire(
              'Producto eliminado!',
              'El producto se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }
  
  abrirModal(producto: Producto){
    this.productoSeleccionado= producto;
    this.modalservice.abrirModal();
  }

  crearProducto(){
    this.productoSeleccionado = new Producto();
    this.modalservice.abrirModal();
  }

  getProductos(){
    this.productos= null;
    this.elements = [];
    this.previous = [];
    this.procesosService.getProductos(this.proceso.procesosId).subscribe(response => {
      this.productos = response;
      if(this.productos.length >0){
        this.productos.forEach(pro =>{
          this.elements.push({procesoProductoId: pro.procesoProductoId, procesoProductoServicio: pro.procesoProductoServicio, procesoProductoCaracteristica: pro.procesoProductoCaracteristica,
            proceso: pro.proceso
          });
        });
      }
    });
  }

}
