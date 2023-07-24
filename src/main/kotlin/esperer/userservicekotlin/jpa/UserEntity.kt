package esperer.userservicekotlin.jpa

import javax.persistence.*

@Entity
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false, length = 50)
    val email: String,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, length = 50, unique = true)
    val userId: String,

    @Column(nullable = false, length = 50, unique = true)
    val encryptedPassword: String
)