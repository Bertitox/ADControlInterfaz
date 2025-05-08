#!/bin/bash
# Variables
USUARIO="parallels"                # Usuario del equipo remoto
HOST_REMOTO="192.168.0.24"      # Dirección IP o nombre del equipo remoto
GET_REPOSITORIO="git clone https://github.com/Bertitox/ADControl.git"
SCRIPT_REMOTO="bash ./ADControl/ScriptV2.sh"  # Ruta al script en el equipo remoto
RUTA_JSON_REMOTO="./system_info.json"    # Ruta al JSON generado por el script remoto
RUTA_LOCAL="src/main/resources/org/example/adcontrol/ResultadoScript/"                          # Ruta en el equipo anfitrión para guardar el JSON
PASSWORD="usuarioxd"        # Contraseña de sudo del equipo remoto

# 1. Verificar si existe una clave SSH local
if [ ! -f "/Users/alber/.ssh/id_rsa.pub" ]; then
  echo "No se encontró una clave SSH local. Generando una nueva..."
  ssh-keygen -t rsa -b 4096 -f "/home/alber/.ssh/id_rsa" -q -N ""
  echo "Clave SSH generada correctamente."
else
  echo "Clave pública encontrada en /Users/alber/.ssh/id_rsa.pub."
fi

# 2. Copiar la clave pública al equipo remoto
echo "Copiando clave pública al equipo remoto..."
sshpass -p "$PASSWORD" ssh-copy-id -o StrictHostKeyChecking=no -i "/Users/alber/.ssh/id_rsa.pub" "$USUARIO@$HOST_REMOTO"
if [ $? -ne 0 ]; then
  echo "Error al copiar la clave pública. Verifica las credenciales o la conexión al equipo remoto."
  exit 1
fi

# 3. Verificar si Git está instalado en el equipo remoto, si no, instalarlo
echo "Comprobando si Git está instalado en el equipo remoto..."
sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USUARIO@$HOST_REMOTO" "which git >/dev/null 2>&1"
if [ $? -ne 0 ]; then
  echo "Git no está instalado. Instalando Git..."
  sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USUARIO@$HOST_REMOTO" "echo '$PASSWORD' | sudo -S apt update && echo '$PASSWORD' | sudo -S apt install -y git"
  if [ $? -ne 0 ]; then
    echo "Error al instalar Git en el equipo remoto."
    exit 1
  else
    echo "Git instalado correctamente."
  fi
else
  echo "Git ya está instalado en el equipo remoto."
fi

# 4. Descargar repositorio de GitHub
echo "Obteniendo el repositorio..."
sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USUARIO@$HOST_REMOTO" "$GET_REPOSITORIO"
if [ $? -ne 0 ]; then
  echo "Error al obtener el repositorio."
  exit 1
fi

# 4. Ejecutar el script remoto para generar el JSON
echo "Generando json..."
sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USUARIO@$HOST_REMOTO" "$SCRIPT_REMOTO"
if [ $? -ne 0 ]; then
  echo "Error al generar el json."
  exit 1
fi

# 5. Transferir el archivo JSON al equipo anfitrión
echo "Transfiriendo el archivo JSON al equipo anfitrión..."
sshpass -p "$PASSWORD" scp -o StrictHostKeyChecking=no "$USUARIO@$HOST_REMOTO:$RUTA_JSON_REMOTO" "$RUTA_LOCAL"
if [ $? -eq 0 ]; then
  echo "JSON transferido correctamente a $RUTA_LOCAL"
else
  echo "Hubo un problema durante la transferencia."
  exit 1
fi

# 6. Eliminar carpeta ADControl y system_info.json en el equipo remoto
echo "Eliminando carpeta ADControl y archivo JSON en el equipo remoto..."
sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USUARIO@$HOST_REMOTO" "rm -rf ./ADControl && rm -f ./system_info.json"
if [ $? -eq 0 ]; then
  echo "Limpieza completada correctamente en el equipo remoto."
else
  echo "Hubo un problema al eliminar archivos en el equipo remoto."
  exit 1
fi
