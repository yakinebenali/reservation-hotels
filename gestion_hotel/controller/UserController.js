const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const connection = require('./../db');

const registerUser = (req, res) => {
    const { email, password, nom, numero_telephone } = req.body;

    if (!email || !password || !nom) {
        return res.status(400).json({ error: "Tous les champs obligatoires doivent être remplis." });
    }

    connection.query('SELECT * FROM users WHERE email = ?', [email], (err, results) => {
        if (err) {
            console.error("Erreur lors de la vérification de l'utilisateur:", err);
            return res.status(500).json({ error: "Erreur serveur" });
        }

        if (results.length > 0) {
            return res.status(400).json({ error: "Cet email est déjà utilisé." });
        }

        bcrypt.hash(password, 10, (err, hashedPassword) => {
            if (err) {
                console.error("Erreur lors du hachage du mot de passe:", err);
                return res.status(500).json({ error: "Erreur serveur" });
            }

            const sql = `
                INSERT INTO users (email, password, nom, numero_telephone)
                VALUES (?, ?, ?, ?)
            `;
            connection.query(sql, [email, hashedPassword, nom, numero_telephone], (err, results) => {
                if (err) {
                    console.error("Erreur lors de l'enregistrement de l'utilisateur:", err);
                    return res.status(500).json({ error: "Erreur lors de l'enregistrement." });
                }

                res.status(201).json({
                    message: "Utilisateur inscrit avec succès",
                    id_user: results.insertid_user
                });
            });
        });
    });
};

const loginUser = (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ error: "Email et mot de passe sont requis." });
    }

    connection.query('SELECT * FROM users WHERE email = ?', [email], (err, results) => {
        if (err) {
            console.error("Erreur lors de la récupération de l'utilisateur:", err);
            return res.status(500).json({ error: "Erreur serveur" });
        }

        if (results.length === 0) {
            return res.status(404).json({ error: "Utilisateur non trouvé." });
        }

        const user = results[0];

        bcrypt.compare(password, user.password, (err, isMatch) => {
            if (err) {
                console.error("Erreur lors de la comparaison des mots de passe:", err);
                return res.status(500).json({ error: "Erreur serveur" });
            }

            if (!isMatch) {
                return res.status(400).json({ error: "Mot de passe incorrect." });
            }

            const token = jwt.sign(
                {id_user: user.id_user, email: user.email },
                'votre_clé_secrète',
                { expiresIn: '1h' }
            );

            res.status(200).json({
                message: "Connexion réussie",
                token,
                id_user:user.id_user,
                nom:user.nom
            });
        });
    });
};
const getUserInfo = (req, res) => {
    const { id_user } = req.params;  

    if (!id_user) {
        return res.status(400).json({ error: "ID de l'utilisateur est requis." });
    }

    const sql = `
        SELECT nom,numero_telephone, email
        FROM users
        WHERE id_user = ?
    `;

    connection.query(sql, [id_user], (err, results) => {
        if (err) {
            console.error("Erreur lors de la récupération des informations utilisateur:", err);
            return res.status(500).json({ error: "Erreur serveur" });
        }

        if (results.length === 0) {
            return res.status(404).json({ message: "Aucun utilisateur trouvé avec cet ID." });
        }

    
        res.status(200).json(results[0]);
    });
};


module.exports = { registerUser, loginUser,getUserInfo };
