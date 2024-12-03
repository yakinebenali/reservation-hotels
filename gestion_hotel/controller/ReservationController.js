const connection = require('./../db');  


const addReservation = (req, res) => {
  const { id_user, id_hotel, nom_hotel, prix_par_jour, nbre_jours, date_reservation, commentaire, methode_paiement } = req.body;

  if (!id_user || !id_hotel || !nom_hotel || !prix_par_jour || !nbre_jours || !date_reservation || !methode_paiement) {
    return res.status(400).json({ error: "Tous les champs obligatoires doivent être remplis." });
  }
const total = prix_par_jour * nbre_jours;
  const sql = `
    INSERT INTO reservation_hotel 
    (id_user, id_hotel, nom_hotel, prix_par_jour, total, nbre_jours, date_reservation, statut, commentaire, methode_paiement)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
  `;
  const values = [id_user, id_hotel, nom_hotel, prix_par_jour, total, nbre_jours, date_reservation, 'En attente', commentaire || null, methode_paiement];
 connection.query(sql, values, (err, results) => {
    if (err) {
      console.error("Erreur lors de l'ajout de la réservation:", err);
      return res.status(500).json({ error: "Erreur lors de l'ajout de la réservation." });
    }
 res.status(201).json({
      message: "Réservation ajoutée avec succès",
      reservation_id: results.insertId,
    });

    console.log("Réservation ajoutée avec succès, ID de la réservation:", results.insertId);
  });
};
const getReservations = (req, res) => {
    const { id_user } = req.params; 
    if (!id_user) {
      return res.status(400).json({ error: "ID de l'utilisateur est requis." });
    }
  
  
    const sql = `
      SELECT * FROM reservation_hotel
      WHERE id_user = ?
    `;
 
    connection.query(sql, [id_user], (err, results) => {
      if (err) {
        console.error("Erreur lors de la récupération des réservations:", err);
        return res.status(500).json({ error: "Erreur serveur" });
      }
  
      if (results.length === 0) {
        return res.status(404).json({ message: "Aucune réservation trouvée pour cet utilisateur." });
      }
  
      res.status(200).json(results);
    });
  };
  const updateReservationStatus = (req, res) => {
    const { id_reservation } = req.params;
    const { statut } = req.body; 
    const validStatuses = ['en attente', 'confirmé', 'annulé', 'en cours'];
    if (!validStatuses.includes(statut)) {
        return res.status(400).json({ error: "Statut invalide. Choisissez parmi: 'en attente', 'confirmé', 'annulé', 'en cours'." });
    }

    if (!id_reservation || !statut) {
        return res.status(400).json({ error: "ID de la réservation et statut sont requis." });
    }

    const sql = `
        UPDATE reservation_hotel
        SET statut = ?
        WHERE id_reservation = ?
    `;

    connection.query(sql, [statut, id_reservation], (err, results) => {
        if (err) {
            console.error("Erreur lors de la mise à jour du statut de la réservation:", err);
            return res.status(500).json({ error: "Erreur serveur" });
        }

        if (results.affectedRows === 0) {
            return res.status(404).json({ message: "Aucune réservation trouvée avec cet ID." });
        }

        res.status(200).json({ message: "Statut de la réservation mis à jour avec succès." });
    });
};

module.exports = { addReservation ,getReservations,updateReservationStatus};
