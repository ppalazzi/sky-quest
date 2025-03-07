import { useEffect } from 'react';

export const Dashboard = () => {

	async function getData() {
		console.log("Fetching...");

		const response = await fetch("https://jsonplaceholder.typicode.com/todos/1");
		const data = await response.json();

		console.log(data);
	}


	useEffect(() => {
		getData();
	})

  return (
    <div>
      Dashboard
    </div>
  )
}