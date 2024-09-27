var movies = [
	{
		"id": 1,
		"title": "The Shawshank Redemption",
		"overview": "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
		"releaseDate": "1994-09-23",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 2,
		"title": "The Godfather",
		"overview": "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
		"releaseDate": "1972-03-24",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 3,
		"title": "The Dark Knight",
		"overview": "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.",
		"releaseDate": "2008-07-18",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 4,
		"title": "Pulp Fiction",
		"overview": "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
		"releaseDate": "1994-10-14",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 5,
		"title": "Forrest Gump",
		"overview": "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold from the perspective of an Alabama man with an IQ of 75.",
		"releaseDate": "1994-07-06",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 6,
		"title": "Inception",
		"overview": "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
		"releaseDate": "2010-07-16",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 7,
		"title": "Fight Club",
		"overview": "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into something much, much more.",
		"releaseDate": "1999-10-15",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 8,
		"title": "The Matrix",
		"overview": "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
		"releaseDate": "1999-03-31",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 9,
		"title": "Goodfellas",
		"overview": "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito.",
		"releaseDate": "1990-09-21",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	},
	{
		"id": 10,
		"title": "The Lord of the Rings: The Fellowship of the Ring",
		"overview": "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
		"releaseDate": "2001-12-19",
		"imageURL": "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"
	}
];
function getMovies(request, response) {
	var msgs = {
		"movies": movies
	};
	
	response.writeHead(200, {"Content-Type": "application/json"});
	response.end(JSON.stringify(msgs));
}

function addMovies(request, response) {
	if (request.body.type == 0) {
		request.body.type = 1;
	}

	movies.push(request.body);
	response.writeHead(200, {"Content-Type": "application/json"});
	response.end(JSON.stringify({status: 1}));
}

exports.getMovies = getMovies;
exports.addMovies = addMovies;