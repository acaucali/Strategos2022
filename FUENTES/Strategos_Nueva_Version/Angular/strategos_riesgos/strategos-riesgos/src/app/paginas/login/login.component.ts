import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Usuario } from '../configuracion/model/usuario';
import { AuthService } from '../configuracion/services-util/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
  usuario: Usuario;

  constructor(private authService: AuthService, private router: Router) { 
    this.usuario = new Usuario();
  }

  ngOnInit() {
    if(this.authService.isAuthenticated()){
      swal.fire('Login',`usuario: ${this.authService.usuario.username} ya estás autenticado`, 'info');
      this.router.navigate(['/pedidos']);
    }
  }

  login(): void{
    
    console.log(this.usuario)

    if(this.usuario.username == null || this.usuario.pwd == null){
      swal.fire( 'Error inicio de sesión','Usuario o contraseña vacías!', 'error');
    }

    this.authService.login(this.usuario).subscribe( response => {
      this.router.navigate(['/banco/procesos']);
      
      this.authService.guardarUsuario(response.access_token);
      this.authService.guardarToken(response.access_token);
      
      let usuario = this.authService.usuario;
      
      swal.fire('Ingreso',`Bienvenido a Strategos, 
      Usuario: ${usuario.username}`, 'success');
    }, error => {
      if(error.status == 400){
        swal.fire('Error Login',`Usuario o clave incorrectas !`, 'error');
      }
    }   
    
    );
  }

}
