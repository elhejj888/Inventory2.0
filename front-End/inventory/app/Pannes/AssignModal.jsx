import { get, set } from 'lodash';
import React, { use, useState , useEffect } from 'react';
import Alert from '../component/Alert';

const AssignTechnician = ({ isOpen , onClose , sess , panneId }) => {
    const session = JSON.parse(sess);
    if (!isOpen) return null;

    const [data, setData] = useState([]);
    const [selectedTechnician, setSelectedTechnician] = useState('');
    const [isOpened, setIsOpened] = useState(false);
    const [message, setMessage] = useState('');
    const [title, setTitle] = useState('');
    const [type, setType] = useState('');

    
    const handleAssign = async (e) => {
        e.preventDefault();
        console.log('selectedTechnician:', selectedTechnician);
        console.log('panneId:', panneId);
        try {
          const response = await fetch(`http://localhost:8080/api/breakdown/assign-${panneId}/tech-${selectedTechnician}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${session.accessToken}`,
            },
          });
          if (!response.ok) {
            setIsOpened(true);
            setMessage('Erreur lors de l\'assignement de la panne..!'); // For debugging or further processing
            setTitle('Assignement de Panne!'); 
            setType('alert');
            throw new Error(`Error: ${response.statusText}`);
          }
          const data = await response.json();
          setIsOpened(true);
          setMessage('Panne Assigné avec succes..!'); // For debugging or further processing
          setTitle('Assignement de Panne!');
          setType('success');
        //   window.location.reload();  // Force a hard refresh of the entire page
        } catch (err) {
          setIsOpened(true);
            setMessage('Erreur lors de l\'assignement de la panne..!'); // For debugging or further processing
            setTitle('Assignement de Panne!'); 
            setType('alert');
          console.error('Failed to assign breakdown:', err.message);
        }
      };
    

    
    //   const handleAssign = (e) => {
    //     e.preventDefault();
    //     if (selectedTechnician) {
    //       // Handle the assignment logic here
    //       console.log('Assigned to technician:', selectedTechnician);
    //     } else {
    //       console.log('No technician selected');
    //     }
    //   };
      const role =  encodeURIComponent('Technicien Materiel');
      const getData = async () => {
        try {
          const response = await fetch(`http://localhost:8080/auth/role`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${session.accessToken}`,
            },
            });
            if (response.ok) {
                const data = await response.json();
                setData(data);
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    useEffect(() => {
        getData();
    }
    , []);
    
      return (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
          <div className="relative bg-white p-6 rounded shadow-lg max-w-md w-full">
            <button
              onClick={onClose}
              className="absolute top-2 right-2 text-xl font-bold bg-gray-200 p-2 rounded-full hover:bg-gray-400 transition duration-300"
            >
              &times;
            </button>
    
            <div className="flex flex-col w-full p-4">
              <h1 className="text-3xl font-bold font-cairo w-full text-center border-b-2 shadow-md py-2 text-blue-900">
                Assign Technician
              </h1>
            </div>
    
            <form onSubmit={handleAssign}>
              <div className="mb-4 flex flex-col space-y-4 p-4 rounded-lg shadow-lg hover:shadow-2xl transition-shadow duration-300">
                <label className="text-black text-lg font-semibold">Technician:</label>
                <select
                  value={selectedTechnician}
                  onChange={(e) => setSelectedTechnician(e.target.value)}
                  className="w-full text-black text-center border-2 border-blue-500 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-300"
                >
                  <option value="">Select a technician</option>
                  {data.map((tech) => (
                    <option key={tech.id} value={tech.id}>
                      {tech.firstName} {tech.lastName}
                    </option>
                  ))}
                </select>
              </div>
    
              <button
                type="submit"
                className="bg-blue-500 w-full text-white p-2 rounded hover:bg-blue-700 transition duration-300"
              >
                Assign
              </button>
            </form>
          </div>
          <Alert
            title={title}
            type={type}
            message={message}
            isOpen={isOpened}
            onClose={() => setIsOpened(false)}
          />
        </div>
      );
    };
    
    export default AssignTechnician;