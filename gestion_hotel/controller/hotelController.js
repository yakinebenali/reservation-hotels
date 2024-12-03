const connection = require('./../db');


const addHotel = (req, res) => {
  const { body, files } = req; 
  const { nom, adresse, ville, heureOuverture, heureFermeture, numero_telephone, email } = body;

 
  if (!nom || !adresse || !ville || !heureOuverture || !heureFermeture || !numero_telephone || !email) {
      return res.status(400).json({ error: "Tous les champs obligatoires doivent être remplis." });
  }
 const logoFile = files.find(file => file.fieldname === 'logoPath');
  const imageFile = files.find(file => file.fieldname === 'imagePath');
 const baseUrl = 'http://172.20.10.8:3000/';
  const logoUrl = logoFile ? `${baseUrl}images/${logoFile.filename}` : null;
  const imageUrl = imageFile ? `${baseUrl}images/${imageFile.filename}` : null;

  if (!logoUrl || !imageUrl) {
      return res.status(400).json({ error: "Les fichiers logo et image sont requis." });
  }

  console.log("Logo URL:", logoUrl);
  console.log("Image URL:", imageUrl);
const sql = `
    INSERT INTO hotel 
    (nom, adresse, ville, logo, image, heure_ouverture, heure_fermeture, numero_telephone, email)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
  `;

  connection.query(
      sql,
      [nom, adresse, ville, logoUrl, imageUrl, heureOuverture, heureFermeture, numero_telephone, email],
      (err, results) => {
          if (err) {
              console.error("Erreur lors de l'ajout de l'hôtel:", err);
              return res.status(500).json({ error: "Erreur lors de l'ajout de l'hôtel." });
          }

          res.status(201).json({ 
              message: "Hôtel ajouté avec succès", 
              hotelid_hotel: results.insertid_hotel 
          });

          console.log("Hôtel ajouté avec succès, id_hotel:", results.insertid_hotel);
      }
  );
};

const getHotels = (req, res) => {
  const sql = `
    SELECT 
      id_hotel, 
      nom, 
      adresse, 
      ville, 
      logo AS logo_url, 
      image AS image_url, 
      heure_ouverture, 
      heure_fermeture, 
      numero_telephone, 
      email
    FROM hotel
  `;

  connection.query(sql, (err, results) => {
      if (err) {
          console.error("Erreur lors de la récupération des hôtels:", err);
          return res.status(500).json({ error: "Erreur lors de la récupération des hôtels." });
      }

      res.status(200).json(results);
  });
};

const editHotel = (req, res) => {
  const { id_hotel } = req.params; // Récupérer l'id_hotel de l'hôtel depuis les paramètres de l'URL
  const { nom, adresse, ville, heureOuverture, heureFermeture, numero_telephone, email } = req.body;

  // Construction de la requête SQL de mise à jour
  let sql = 'UPDATE hotel SET ';
  let values = [];
  let setFields = [];

  // Vérifier chaque champ et n'ajouter à la requête que ceux qui sont fournis
  if (nom) {
    setFields.push('nom = ?');
    values.push(nom);
  }
  if (adresse) {
    setFields.push('adresse = ?');
    values.push(adresse);
  }
  if (ville) {
    setFields.push('ville = ?');
    values.push(ville);
  }
  if (heureOuverture) {
    setFields.push('heure_ouverture = ?');
    values.push(heureOuverture);
  }
  if (heureFermeture) {
    setFields.push('heure_fermeture = ?');
    values.push(heureFermeture);
  }
  if (numero_telephone) {
    setFields.push('numero_telephone = ?');
    values.push(numero_telephone);
  }
  if (email) {
    setFields.push('email = ?');
    values.push(email);
  }

  // Si aucun champ n'a été fourni, renvoyer une erreur
  if (setFields.length === 0) {
    return res.status(400).json({ error: "Aucun champ à mettre à jour n'a été fourni." });
  }

  // Ajouter la condition WHERE pour spécifier quel hôtel mettre à jour
  sql += setFields.join(', ') + ' WHERE id_hotel = ?';
  values.push(id_hotel);

  // Exécuter la requête SQL
  connection.query(sql, values, (err, results) => {
    if (err) {
      console.error("Erreur lors de la mise à jour de l'hôtel:", err);
      return res.status(500).json({ error: "Erreur lors de la mise à jour de l'hôtel." });
    }

    // Si aucun enregistrement n'a été affecté, l'hôtel n'a pas été trouvé
    if (results.affectedRows === 0) {
      return res.status(404).json({ error: "Hôtel non trouvé." });
    }

    // Renvoi d'une réponse avec un message de succès
    res.status(200).json({ message: "Hôtel mis à jour avec succès." });
  });
};

const deleteHotel = (req, res) => {
  const { id_hotel } = req.params; 

  const sql = `DELETE FROM hotel WHERE id_hotel = ?`;

  connection.query(sql, [id_hotel], (err, results) => {
      if (err) {
        console.error("Erreur lors de la suppression de l'hôtel:", err);
        return res.status(500).json({ error: "Erreur lors de la suppression de l'hôtel." });
      }

      if (results.affectedRows === 0) {
        return res.status(404).json({ error: "Hôtel non trouvé." });
      }

      res.status(200).json({ message: "Hôtel supprimé avec succès." });
  });
};

module.exports = { getHotels, addHotel, editHotel, deleteHotel };