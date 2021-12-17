import React from 'react';
import {Canvas,useFrame} from '@react-three/fiber';
//import {OrbitControls} from '@react-three/drei';

export default function(){
  return(
    <>
      <Canvas>
        <CoinMesh></CoinMesh>
      </Canvas>
    </>
  )
}

function CoinMesh() {
    const mesh = useRef(null);
    useFrame(() => (mesh.current.rotation.y =  mesh.current.rotation.z += 0.01)); // #2
    return (
      <mesh ref={mesh} scale={0.7}>
        <cylinderBufferGeometry args={[1, 1, 0.3, 50]} /> // #1
        <meshLambertMaterial attach="material" color="#ff9800" />
      </mesh>
    );
  }